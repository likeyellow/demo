package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class PageServiceImpl implements PageService {
	
	// 자동주입 대상은 final로
	private final ModelMapper modelMapper;
		
	private final TodoRepository todoRepository;

	@Override
	public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
			
		Pageable pageable = 
			PageRequest.of(
				pageRequestDTO.getPage() - 1, // 1페이지가 0이므로 주의
				pageRequestDTO.getSize(),
				Sort.by("tno").descending());
			
		Page<TodoEntity> result = todoRepository.findAll(pageable);
			
		List<TodoDTO> dtoList = result.getContent().stream()
								.map(todo -> modelMapper.map(todo,  TodoDTO.class))
								.collect(Collectors.toList());
			
		long totalCount = result.getTotalElements();
			
		PageResponseDTO<TodoDTO> responseDTO = 
						PageResponseDTO.<TodoDTO>withAll()
							.dtoList(dtoList)
							.pageRequestDTO(pageRequestDTO)
							.totalCount(totalCount)
							.build();
			
			
		return responseDTO;
	}

}
