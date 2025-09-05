package com.ddmtchr.soalab.service;

import com.ddmtchr.soalab.repository.DragonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DragonService {

    private final DragonRepository dragonRepository;


}
