package com.ntd.bank.historyservice.service.impl;

import com.ntd.bank.historyservice.dto.HistoryDto;
import com.ntd.bank.historyservice.dto.PageResult;
import com.ntd.bank.historyservice.model.History;
import com.ntd.bank.historyservice.repository.HistoryRepository;
import com.ntd.bank.historyservice.service.HistoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public HistoryDto save(HistoryDto historyDto) {
        History history = modelMapper.map(historyDto, History.class);
        history = historyRepository.save(history);
        return modelMapper.map(history, HistoryDto.class);
    }

    @Override
    public PageResult<HistoryDto> getAll(Pageable pageable) {
        Page<History> historyPage = historyRepository.findAll(pageable);
        List<HistoryDto> historyDtoList = new LinkedList<>();
        for (History history : historyPage) {
            HistoryDto dto = modelMapper.map(history, HistoryDto.class);
            historyDtoList.add(dto);
        }
        PageResult<HistoryDto> pageResult = new PageResult<>();
        pageResult.setResult(historyDtoList);
        pageResult.setCurrentPage(historyPage.getNumber());
        pageResult.setTotalPage(historyPage.getTotalPages());
        pageResult.setTotalItems(historyPage.getTotalElements());
        return pageResult;
    }
}
