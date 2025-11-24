package ua.nure.kz.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.nure.kz.dto.GroupDTO;
import ua.nure.kz.dto.UserDTO;
import ua.nure.kz.model.Group;
import ua.nure.kz.repository.GroupRepository;
import ua.nure.kz.service.GroupService;
import ua.nure.kz.service.UserService;
import ua.nure.kz.utils.SessionUtil;

@Controller
@RequestMapping("groups")
public class GroupController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("")
    public String groupsList(
            Model model, HttpServletRequest request,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        UserDTO user = SessionUtil.getUserFromSession(request, userService);
        if(user == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("groups", groupService.getGroups(page - 1, pageSize));

        return "groups-list";
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createGroup(
            Model model, HttpServletRequest request,
            @RequestParam("name") String name) {
        UserDTO user = SessionUtil.getUserFromSession(request, userService);
        if(user == null) {
            return "redirect:/auth/login";
        }
        if(!user.isAdmin()) {
            model.addAttribute("error", "Insufficient privileges!");
            return "groups-list";
        }

        Group groupToCreate = new Group();
        groupToCreate.setName(name);
        groupService.createGroup(groupToCreate);

        return "redirect:/groups";
    }

    @GetMapping("edit/{groupId}")
    public String editGroupPage(Model model, HttpServletRequest request, @PathVariable(name = "groupId") long groupId) {
        UserDTO user = SessionUtil.getUserFromSession(request, userService);
        if(user == null) {
            return "redirect:/auth/login";
        }
        if(!user.isAdmin()) {
            return "redirect:/groups";
        }

        GroupDTO targetGroup = groupService.findGroupById(groupId);
        if(targetGroup == null) {
            return "redirect:/groups";
        }

        model.addAttribute("user", user);
        model.addAttribute("group", targetGroup);

        return "groups-edit";
    }

    @PostMapping(value = "edit/{groupId}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String editGroup(
            HttpServletRequest request,
            @PathVariable(name = "groupId") long groupId,
            @RequestParam("name") String name) {
        UserDTO user = SessionUtil.getUserFromSession(request, userService);
        if(user == null) {
            return "redirect:/auth/login";
        }
        if(!user.isAdmin()) {
            return "redirect:/groups";
        }

        Group targetGroup = groupRepository.findGroupById(groupId);
        if(targetGroup == null) {
            return "redirect:/groups";
        }

        if(targetGroup.getName().equals(name)) {
            return "redirect:/groups/edit/" + groupId;
        }

        targetGroup.setName(name);
        groupService.updateGroup(targetGroup);

        return "redirect:/groups/edit/" + groupId;
    }

    @GetMapping("delete/{groupId}")
    public String deleteGroupPage(Model model, HttpServletRequest request, @PathVariable(name = "groupId") long groupId) {
        UserDTO user = SessionUtil.getUserFromSession(request, userService);
        if(user == null) {
            return "redirect:/auth/login";
        }
        if(!user.isAdmin()) {
            return "redirect:/groups";
        }

        GroupDTO targetGroup = groupService.findGroupById(groupId);
        if(targetGroup == null) {
            return "redirect:/groups";
        }

        model.addAttribute("user", user);
        model.addAttribute("group", targetGroup);

        return "groups-delete";
    }

    @PostMapping("delete/{groupId}")
    public String deleteGroup(HttpServletRequest request, @PathVariable(name = "groupId") long groupId) {
        UserDTO user = SessionUtil.getUserFromSession(request, userService);
        if(user == null) {
            return "redirect:/auth/login";
        }
        if(!user.isAdmin()) {
            return "redirect:/groups";
        }

        groupService.deleteGroup(groupId);
        return "redirect:/groups";
    }
}
