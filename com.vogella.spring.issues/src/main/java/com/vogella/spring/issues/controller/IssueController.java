package com.vogella.spring.issues.controller;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ModelAttribute;
import com.vogella.spring.issues.entities.IssueReport;
import com.vogella.spring.issues.repositories.IssueRepository;

@Controller
public class IssueController {
	
	IssueRepository issueRepository;
	
	public IssueController(IssueRepository issueRepository) {
		this.issueRepository = issueRepository;
	}
	
	//This class contains the methods responsible for handling incoming web requests.
	
	@GetMapping("/issuereport")
//	@ResponseBody
	public String getReport(Model model, @RequestParam(name = "submitted", required = false) boolean submitted) {
		model.addAttribute("submitted", submitted);
		model.addAttribute("issuereport", new IssueReport());
		return "issues/issuereport_form";
	}
	
	@PostMapping("/issuereport")
//	@ResponseBody
	public String submitReport(IssueReport issueReport, RedirectAttributes  ra) {
		this.issueRepository.save(issueReport);
		ra.addAttribute("submitted", true);
		return "redirect:/issuereport";
		
		//Saves the issueReport object to the database and returns the saved object. 
		//You should always continue with the entity returned by the repository, 
		//because it contains the id set by the database and might contain other changes done in the database too.
	}
	
	//the getIssues() method will handle the HTML template for a list view in which all the requests can be viewed. 
	//This method will return a template with a list of all reports that were submitted.
	
	@GetMapping("/issues")
//	@ResponseBody
	public String getIssues(Model model) {
		model.addAttribute("issues", this.issueRepository.findAllButPrivate());
		return "issues/issuereport_list";
	}
}
