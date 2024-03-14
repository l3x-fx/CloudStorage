package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {
    private NoteMapper noteMapper;

    public List<Note> getNotes(String userId) {
        return noteMapper.getNotes(userId);
    }
    public int addNote(NoteForm noteForm, User user){
        return noteMapper.insertNote(new Note(null, noteForm.getNoteTitle(), noteForm.getNoteDescription(), user.getUserId() ));
    }
    public int updateNote(NoteForm noteForm, User user) {
        return noteMapper.updateNote(new Note(null, noteForm.getNoteTitle(), noteForm.getNoteDescription(), user.getUserId()));
    }
    public int deleteNote(String noteId){
        return noteMapper.deleteNote(noteId);
    }


}
