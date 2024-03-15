package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    NoteService noteService;
    @Autowired
    UserService userService;

    @GetMapping()
    public String homeView(NoteForm noteForm, Authentication auth, Model model) {
        User user = userService.getUser(auth.getName());

        model.addAttribute("notes", this.noteService.getNotes(user.getUserId()));
        return "home";
    }

//    NOTES
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
                model.addAttribute("errorMessage", "Your note was not saved.Try again.");
            }
        } else {
            int result = noteService.updateNote(noteForm, user);
            if (result == 1) {
                model.addAttribute("success", true);
                model.addAttribute("successMessage", "Changes successfully saved!");
            } else {
                model.addAttribute("success", false);
                model.addAttribute("errorMessage", "Your changes were not saved.Try again.");
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
            model.addAttribute("errorMessage", "Your note was not deleted. Try again.");
        }
        return "result";
    }
}

