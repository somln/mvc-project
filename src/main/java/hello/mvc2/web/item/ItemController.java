package hello.mvc2.web.item;

import hello.mvc2.domain.item.DeliveryCode;
import hello.mvc2.domain.item.Item;
import hello.mvc2.domain.item.ItemRepository;
import hello.mvc2.domain.item.ItemType;
import hello.mvc2.web.item.form.ItemSaveForm;
import hello.mvc2.web.item.form.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemRepository itemRepository;

    @ModelAttribute("regions")
    public Map<String, String> regions(){
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes(){
        return ItemType.values();
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes(){
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }


    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "items/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "items/item";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("item", new Item());
        return "items/addItemForm";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("item") ItemSaveForm form,
                          BindingResult bindingResult, RedirectAttributes redirectAttributes){

            if(form.getPrice()!=null && form.getQuantity()!=null){
            int resultPrice = form.getPrice() * form.getQuantity();
            if(resultPrice<10000){
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "items/addItemForm";
        }

        Item item = new Item();
        SaveFormToItem(form, item);

        Item savedItem = itemRepository.save(item);
        log.info("item={}", item.toString());

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/items/{itemId}";
    }

    public void SaveFormToItem(ItemSaveForm form, Item item){
        item.setItemName(form.getItemName());
        item.setPrice(form.getPrice());
        item.setQuantity(form.getQuantity());
        item.setOpen(form.getOpen());
        item.setRegions(form.getRegions());
        item.setItemType(form.getItemType());
        item.setDeliveryCode(form.getDeliveryCode());
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "items/editItemForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editItem(@Validated @PathVariable Long itemId,
                           @ModelAttribute("item") ItemUpdateForm form, BindingResult bindingResult){

        if(form.getPrice()!=null && form.getQuantity()!=null){
            int resultPrice = form.getPrice() * form.getQuantity();
            if(resultPrice<10000){
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if(bindingResult.hasErrors()){
            return "items/editItemForm";
        }

        Item item = new Item();
        EditFormToItem(form, item);

        itemRepository.update(itemId, item);
        return "redirect:/items/{itemId}";
    }

    public void EditFormToItem(ItemUpdateForm form, Item item){
        item.setItemName(form.getItemName());
        item.setPrice(form.getPrice());
        item.setQuantity(form.getQuantity());
        item.setOpen(form.getOpen());
        item.setRegions(form.getRegions());
        item.setItemType(form.getItemType());
        item.setDeliveryCode(form.getDeliveryCode());
    }


}
