package io.student.rococo.service.impl;

import io.student.rococo.data.repository.CountryRepository;
import io.student.rococo.model.CountryJson;
import io.student.rococo.service.api.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CountryJson> getAllCountries(Pageable pageable) {
        return countryRepository.findAll(pageable)
                .map(CountryJson::fromEntity);
    }
}