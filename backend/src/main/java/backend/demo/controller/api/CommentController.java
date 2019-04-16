package backend.demo.controller.api;


import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequestMapping()
public interface CommentController {

    @RequestMapping(method = GET , value = "/analysis")
    int getComment();
}