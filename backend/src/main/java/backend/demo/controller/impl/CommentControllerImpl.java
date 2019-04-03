package backend.demo.controller.impl;

import backend.demo.controller.api.CommentController;
import backend.demo.jna.api.JNAApiInterface;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
