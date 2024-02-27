package com.crmapp.crm.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class NameParserService {
    // File cut frist name and lastname
    public String[] parseFullName(String fullname) {
        return fullname.split("\\s+");
    }

    public String[] getFirstNameAndLastName(String fullname) {
        String[] names = parseFullName(fullname);

        if (names.length == 1) {
            return new String[]{names[0], ""}; // Chỉ có một từ, giả sử đó là firstname
        } else if (names.length > 1) {
            String firstname = String.join(" ", Arrays.copyOfRange(names, 0, names.length - 1));
            String lastname = names[names.length - 1];
            return new String[]{firstname, lastname};
        } else {
            return new String[]{"", ""}; // Trường hợp không hợp lệ
        }
    }
}
