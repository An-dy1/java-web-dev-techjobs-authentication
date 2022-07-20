package org.launchcode.javawebdevtechjobsauthentication.controllers;

import org.apache.tomcat.util.http.parser.HttpParser;
import org.launchcode.javawebdevtechjobsauthentication.models.data.JobRepository;
import org.launchcode.javawebdevtechjobsauthentication.models.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private AuthenticationController authController;

    @RequestMapping("")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("jobs", jobRepository.findAll());
        model.addAttribute("activeSession", authController.isActiveSession(request.getSession()));
        model.addAttribute("user", authController.getUserFromSession(request.getSession()));
        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model, HttpServletRequest request) {
        model.addAttribute("activeSession", authController.isActiveSession(request.getSession()));
        model.addAttribute(new Job());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors) {

        if (errors.hasErrors()) {
            return "add";
        }

        jobRepository.save(newJob);
        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId, HttpServletRequest request) {

        Optional optJob = jobRepository.findById(jobId);
        if (!optJob.isEmpty()) {
            Job job = (Job) optJob.get();
            model.addAttribute("job", job);
            model.addAttribute("activeSession", authController.isActiveSession(request.getSession()));
            return "view";
        } else {
            model.addAttribute("activeSession", authController.isActiveSession(request.getSession()));
            return "redirect:/";
        }
    }


}
