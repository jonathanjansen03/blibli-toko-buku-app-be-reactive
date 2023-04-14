package com.example.bliblitokobukuappbereactive.service;

import com.example.bliblitokobukuappbereactive.dto.BookDTO;

public interface BookListenerService {
	void onEventConsumed(BookDTO book);
}
