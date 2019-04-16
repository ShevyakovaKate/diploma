package backend.demo.controller.impl;

import backend.demo.controller.api.CommentController;
import backend.demo.jna.api.JNAApiInterface;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CommentControllerImpl implements CommentController {

    @Override
    public int getComment() {
       return CGetComment(new String("Hello"));
    }

    private int CGetComment(String args) {
        JNAApiInterface jnaLib = JNAApiInterface.INSTANCE;
        return jnaLib.sum(1, 2);
    }

}
