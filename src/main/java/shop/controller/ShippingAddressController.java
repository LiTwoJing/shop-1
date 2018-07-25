package shop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import shop.model.ShippingAddress;
import shop.service.ShippingAddressService;

@Controller
public class ShippingAddressController {
    private ShippingAddressService shippingAddressService;
    
    @Autowired
    public ShippingAddressController(ShippingAddressService shippingAddressService) {
        this.shippingAddressService = shippingAddressService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/uc/shipping-addresses/")
    public String list(Model model,
                       @AuthenticationPrincipal(expression = "user.id") Long userId) {
        List<ShippingAddress> shippingAddresses = shippingAddressService.findAll(userId);
        model.addAttribute("shippingAddresses", shippingAddresses);
        return "shipping-address-list";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/uc/shipping-addresses/add")
    public String add(@ModelAttribute ShippingAddress shippingAddress) {
        return "shipping-address-add";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/uc/shipping-addresses/add")
    public String create(@ModelAttribute @Valid ShippingAddress shippingAddress,
                         BindingResult bindingResult, 
                         @AuthenticationPrincipal(expression = "user.id") Long userId) {
        if (bindingResult.hasErrors()) {
            return "shipping-address-add";
        }
        shippingAddress.setUserId(userId);
        shippingAddressService.create(shippingAddress);
        return "redirect:/uc/shipping-addresses/";
    }    
}
