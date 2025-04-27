package com.example.springtutorial.controller;

import java.util.List;

import org.springframework.core.Conventions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springtutorial.entity.User;
import com.example.springtutorial.form.UserRegisterForm;
import com.example.springtutorial.service.UserService;

@Controller
public class AdminUserController {
	private final UserService userService ;
	
	public AdminUserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/adminuser")
	public String adminUser(Model model) {
		//最新のユーザーリスト
		List<User> users =userService.getAllUsers();
		
		//ビューにユーザーリストを渡す
		model.addAttribute("users", users);
		
		//すでにインスタンスが存在する場合は行わない
		if (!model.containsAttribute("userRegisterForm")) {
			//ビューにフォームクラスのインスタンスを渡す
			model.addAttribute("userRegisterForm", new UserRegisterForm());
		}
		
		return "adminUserView";
	}
	
	@PostMapping("/register")
	public String registerUser(RedirectAttributes redirectAttributes,
			@Validated UserRegisterForm form, BindingResult result) {
			//バリデーショエラーがあったら終了
			if(result.hasErrors()) {
				//フォームクラスをビューに受け渡す
				redirectAttributes.addFlashAttribute("userRegisterForm", form);
				//バリデーショ結果をビューに受け渡す
				redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX
						+ Conventions.getVariableName(form), result);
				
				//adminuserにリダイレクトしてリストを再表示
				return "redirect:/adminuser";
			}
			
		try {
			// ユーザー登録
			userService.createUser(form.getUserName(), form.getPassword(), form.getRoleId());

			// 成功メッセージ
			redirectAttributes.addFlashAttribute("successMessage", "ユーザー登録が完了しました");
		} catch (IllegalArgumentException e) {
			// 失敗メッセージ
			redirectAttributes.addFlashAttribute("failureMessage", e.getMessage());
			
			//フォームクラスをビューに受け渡す
			redirectAttributes.addFlashAttribute("userRegisterForm", form);
			
		}

		// ユーザー一覧にリダイレクト
		return "redirect:/adminuser";
	}

}
