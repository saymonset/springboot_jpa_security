package com.apress.prospring5.ch8.service;
import com.apress.prospring5.ch8.view.SingerSummary;
import java.util.List;
/**
 * Created by simon on 26/09/18.
 */
public interface SingerSummaryService {
    List<SingerSummary> findAll();
}
