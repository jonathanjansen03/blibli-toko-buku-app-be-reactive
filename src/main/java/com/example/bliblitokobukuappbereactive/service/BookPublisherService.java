package com.example.bliblitokobukuappbereactive.service;

import com.example.bliblitokobukuappbereactive.model.dto.BookDTO;

public interface BookPublisherService {
	void sendMessage(BookDTO book);
}
