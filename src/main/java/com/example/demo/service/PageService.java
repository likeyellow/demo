package com.example.demo.service;

import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import com.example.demo.dto.TodoDTO;

public interface PageService {
	
	PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO);

}
