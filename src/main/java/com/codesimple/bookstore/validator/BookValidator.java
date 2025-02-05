package com.codesimple.bookstore.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.codesimple.bookstore.common.Error;
import com.codesimple.bookstore.dto.BookRequestDTO;

@Component
public class BookValidator {

	public List<Error> validateCreateBookRequest(BookRequestDTO bookDTO) {

		List<Error> errors = new ArrayList<>();

		// name
		if (bookDTO.getName() == null) {
			Error error = new Error("name", "book name is null");
			errors.add(error);
		}

		// yop
		if (bookDTO.getYearOfPublication() == null) {
			Error error = new Error("yop", "yop is null");
			errors.add(error);
		}

		// book type
		if (bookDTO.getBookType() == null) {
			errors.add(new Error("bookType", "bookType is null"));
		}

		return errors;
	}
}
