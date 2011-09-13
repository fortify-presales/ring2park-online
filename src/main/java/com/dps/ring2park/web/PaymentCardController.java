package com.dps.ring2park.web;

import java.security.Principal;
import java.util.List;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dps.ring2park.domain.PaymentCard;
import com.dps.ring2park.service.PaymentCardService;
import com.dps.ring2park.web.extensions.FlashMap;

@Controller
@RequestMapping("/cards/*")
public class PaymentCardController {

	@Autowired
	private PaymentCardService paymentCardService;

	// list all of the users PaymentCards - form
	@RequestMapping(method = RequestMethod.GET)
	public String listForm(Model model, Principal currentUser) {
		List<PaymentCard> paymentCards = null;
		if (currentUser != null) {
			paymentCards = paymentCardService.findPaymentCards(currentUser.getName());
		}
		model.addAttribute("paymentCardList", paymentCards);
		return "cards/list";
	}

	// view a specific PaymentCard - form
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public String viewForm(@PathVariable Long id, Model model) {
		PaymentCard paymentCard;
		try {
			paymentCard = paymentCardService.findPaymentCardById(id);
		} catch (NoResultException e) {
			return "cards/invalidPaymentCard";
		}
		model.addAttribute(paymentCard);
		return "cards/view";
	}

	// edit a specific PaymentCard - form
	@RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
	public String editForm(@PathVariable Long id, Model model) {
		PaymentCard paymentCard;
		List<String> types = paymentCardService.getTypes();
		model.addAttribute("typeList", types);
		try {
			paymentCard = paymentCardService.findPaymentCardById(id);
		} catch (NoResultException e) {
			return "cards/invalidPaymentCard";
		}
		model.addAttribute(paymentCard);
		return "cards/edit";
	}

	// delete a specific PaymentCard - form
	@RequestMapping(value = "{id}/delete", method = RequestMethod.GET)
	public String deleteForm(@PathVariable Long id, Model model) {
		PaymentCard paymentCard;
		try {
			paymentCard = paymentCardService.findPaymentCardById(id);
		} catch (NoResultException e) {
			return "cards/invalidPaymentCard";
		}
		model.addAttribute(paymentCard);
		return "cards/delete";
	}

	// add a new paymentCard - form
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addForm(Model model) {
		List<String> types = paymentCardService.getTypes();
		model.addAttribute("typeList", types);
		model.addAttribute(new PaymentCard());
		return "cards/add";
	}

	// REST style action URIs

	// update a paymentCard
	@RequestMapping(method = RequestMethod.POST)
	public String update(@Valid PaymentCard paymentCard, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			List<String> types = paymentCardService.getTypes();
			model.addAttribute("typeList", types);
			return "cards/edit";
		}
		paymentCardService.updatePaymentCard(paymentCard);
		String message = "Succesfully updated Payment Card " + paymentCard.getNumber() + ".";
		FlashMap.setSuccessMessage(message);
		return "redirect:/cards/" + paymentCard.getId();
	}

	// add a paymentCard
	@RequestMapping(method = RequestMethod.PUT)
	public String add(@Valid PaymentCard paymentCard, BindingResult bindingResult,
			Principal currentUser, Model model) {
		if (bindingResult.hasErrors()) {
			List<String> types = paymentCardService.getTypes();
			model.addAttribute("typeList", types);
			return "cards/add";
		}
		if (currentUser != null) {
			paymentCardService.addPaymentCard(paymentCard, currentUser.getName());
			String message = "Succesfully added Payment Card " + paymentCard.getNumber() + ".";
			FlashMap.setSuccessMessage(message);
			return "redirect:/cards/" + paymentCard.getId();
		} else {
			// TODO: return error
			return "redirect:/cards/add";
		}
	}

	// delete a paymentCard
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Long id, Model model) {
		paymentCardService.deletePaymentCardById(id);
		String message = "Succesfully deleted Payment Card.";
		FlashMap.setSuccessMessage(message);
		return "redirect:../cards/";
	}

}