package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    NoteService noteService;

    @GetMapping()
    public String homeView(NoteForm noteForm, User user, Model model) {
        model.addAttribute("notes", this.noteService.getNotes(user.getUserId()));
        return "home";
    }

//    NOTES
    @PostMapping("/note")
    public int addNote(@ModelAttribute NoteForm noteForm, User user, Model model) {
        return noteService.addNote(noteForm, user);
    }
}
