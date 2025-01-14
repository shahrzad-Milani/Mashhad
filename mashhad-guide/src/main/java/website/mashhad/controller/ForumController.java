package website.mashhad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import website.mashhad.model.Message;
import website.mashhad.model.Reply;
import website.mashhad.repository.MessageRepository;
import website.mashhad.repository.ReplyRepository;

@Controller
public class ForumController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ReplyRepository replyRepository;

    // Show the contact page with all messages
    @GetMapping("/contact")
    public String showContactPage(Model model) {
        model.addAttribute("messages", messageRepository.findAll(Sort.by(Sort.Order.desc("id"))));
        model.addAttribute("message", new Message());
        return "contact";
    }

    // Post a new message to the forum
    @PostMapping("/forum/post")
    public String postMessage(@ModelAttribute Message message, RedirectAttributes redirectAttributes) {
        messageRepository.save(message);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/contact";
    }

    // Show message details with replies
    @GetMapping("/forum/message/{id}")
    public String viewMessage(@PathVariable("id") Long id, Model model) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        // Add message and replies to the model
        model.addAttribute("message", message);
        model.addAttribute("reply", new Reply());  // Create an empty reply object for the form
        model.addAttribute("replies", replyRepository.findByMessageIdOrderByRepliedAtDesc(id));  // Get replies ordered by date

        return "message-detail";
    }

    // Post a reply to a specific message
    @PostMapping("/forum/reply/{id}")
    public String postReply(@PathVariable("id") Long id, @ModelAttribute Reply reply, RedirectAttributes redirectAttributes) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        reply.setId(null);

        reply.setMessage(message);

        replyRepository.save(reply);

        redirectAttributes.addFlashAttribute("replySuccess", "Your reply has been posted.");

        return "redirect:/forum/message/" + id;
    }
    // Static pages (Home, About, Attractions, Food)
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to the Home Page");
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("message", "Welcome to the About Page");
        return "about";
    }

    @GetMapping("/attractions")
    public String attractions(Model model) {
        model.addAttribute("message", "Welcome to the Attractions Page");
        return "attractions";
    }

    @GetMapping("/food")
    public String food(Model model) {
        model.addAttribute("message", "Welcome to the Food & Drinks Page");
        return "food";
    }
}
