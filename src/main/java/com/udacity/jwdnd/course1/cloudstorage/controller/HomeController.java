package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    NoteService noteService;
    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;
    @Autowired
    CredentialService credentialService;


    @GetMapping()
    public String homeView(NoteForm noteForm, CredentialForm credentialForm, Authentication auth, Model model) {
        User user = userService.getUser(auth.getName());

        model.addAttribute("notes", this.noteService.getNotes(user.getUserId()));
        model.addAttribute("files", this.fileService.getFiles(user.getUserId()));
        model.addAttribute("credentials", this.credentialService.getCredentials(user.getUserId()));
        model.addAttribute("credentialService", credentialService);
        return "home";
    }
//  FILES
    @PostMapping("/file/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile uploadFile, Model model, Authentication auth) throws IOException {
        User user = userService.getUser(auth.getName());

        int result = fileService.uploadFile(uploadFile, user);
        if (result == 1) {
            model.addAttribute("success", true);
            model.addAttribute("successMessage", "File successfully saved!");
        } else {
            model.addAttribute("success", false);
            model.addAttribute("errorMessage", "Your file was not saved.Try again.");
        }
        return "result";
    }

    @GetMapping("/file/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("id") String fileId, Authentication auth) {
        User user = userService.getUser(auth.getName());

        File result = fileService.downloadFile(user, fileId);
        ByteArrayResource resource = new ByteArrayResource(result.getFileData());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+result.getFileName() + "\" ")
                .body(resource);
    }

    @GetMapping("/file/delete")
    public String deleteFile(@RequestParam("id") String fileId, Model model ) {

        int result = fileService. deleteFile(fileId);

        if (result == 1) {
            model.addAttribute("success", true);
            model.addAttribute("successMessage", "File successfully deleted!");
        } else {
            model.addAttribute("success", false);
            model.addAttribute("errorMessage", "Your file could not be deleted. Try again.");
        }
        return "result";
    }



//  NOTES
    @PostMapping("/note/addoredit")
        public String addNote(Authentication auth, NoteForm noteForm, Model model) {
        User user = userService.getUser(auth.getName());

        if (Objects.equals(noteForm.getNoteId(), "")) {
            int result = noteService.addNote(noteForm, user);

            if (result == 1) {
                model.addAttribute("success", true);
                model.addAttribute("successMessage", "Note successfully saved!");
            } else {
                model.addAttribute("success", false);
                model.addAttribute("errorMessage", "Your note could not be saved.Try again.");
            }
        } else {
            int result = noteService.updateNote(noteForm, user);
            if (result == 1) {
                model.addAttribute("success", true);
                model.addAttribute("successMessage", "Changes successfully saved!");
            } else {
                model.addAttribute("success", false);
                model.addAttribute("errorMessage", "Your changes could not be saved.Try again.");
            }
        }
    return("result");
    }
    @GetMapping("/note/delete")
    public String deleteNote(@RequestParam("id") String noteId, Model model) {

        int result = noteService.deleteNote(noteId);

        if (result == 1) {
            model.addAttribute("success", true);
            model.addAttribute("successMessage", "Note successfully deleted!");
        } else {
            model.addAttribute("success", false);
            model.addAttribute("errorMessage", "Your note could not be deleted. Try again.");
        }
        return "result";
    }


// CREDENTIALS
    @PostMapping("/credential/addoredit")
    public String addCredential(Authentication auth, CredentialForm credentialForm, Model model) {
        User user = userService.getUser(auth.getName());

        if (Objects.equals(credentialForm.getCredId(), "")) {
            int result = credentialService.addCredential(credentialForm, user);

            if (result == 1) {
                model.addAttribute("success", true);
                model.addAttribute("successMessage", "Credential successfully saved!");
            } else {
                model.addAttribute("success", false);
                model.addAttribute("errorMessage", "Your credential could not be saved.Try again.");
            }
        } else {
            int result = credentialService.updateCredential(credentialForm, user);
            if (result == 1) {
                model.addAttribute("success", true);
                model.addAttribute("successMessage", "Changes successfully saved!");
            } else {
                model.addAttribute("success", false);
                model.addAttribute("errorMessage", "Your changes could not be saved.Try again.");
            }
        }
        return("result");
    }

    @GetMapping("/credential/delete")
    public String deleteCredential(@RequestParam("id") String credId, Model model) {

        int result = credentialService.deleteCredential(credId);

        if (result == 1) {
            model.addAttribute("success", true);
            model.addAttribute("successMessage", "Credential successfully deleted!");
        } else {
            model.addAttribute("success", false);
            model.addAttribute("errorMessage", "Your credential could not be deleted. Try again.");
        }
        return "result";
    }

}

