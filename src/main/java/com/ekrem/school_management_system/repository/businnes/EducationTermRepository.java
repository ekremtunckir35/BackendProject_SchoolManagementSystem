package com.ekrem.school_management_system.repository.businnes;

import com.ekrem.school_management_system.entity.concretes.businnes.EducationTerm;

import com.ekrem.school_management_system.entity.enums.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationTermRepository extends JpaRepository<EducationTerm, Long> {

    @Query("select (count (e) >0 ) from EducationTerm e where e.term= ?1 and extact(year from e.startDate) = ?2 ")
    boolean existsByTermAndYear(Term term, int year);

    //var olan education termleri geri donduren sorgu
    @Query("select  e from EducationTerm  e where  extract(year from e.startDate)= ?1 ")
    List<EducationTerm> findByYear(int year);


}