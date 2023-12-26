package com.bank.publicinfo.service.interfaces;

import java.util.List;

public interface MainService<Dto> {
    Dto findById(Long id);

    List<Dto> findAll();

    Dto save(Dto dto);

    Dto update(Long id, Dto dto);

    void deleteById(Long id);
}
