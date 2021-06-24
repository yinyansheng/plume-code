package com.plume.code.impl;


import com.plume.code.GetUserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


@Service
public class GetUserInfoServiceImpl implements GetUserInfoService {

    @Override
    public void getUserInfoById(String id, Model model) {
        //search by id, get UserInfo
        model.addAttribute("name", "name")
                .addAttribute("age", "18")
                .addAttribute("height", "100")
                .addAttribute("weight", "200");
    }
}
