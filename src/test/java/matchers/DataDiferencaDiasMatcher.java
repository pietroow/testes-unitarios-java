package matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import utils.DataUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DataDiferencaDiasMatcher extends TypeSafeMatcher<Date> {

    private Integer qtdDias;

    public DataDiferencaDiasMatcher(Integer qtdDias) {
        this.qtdDias = qtdDias;
    }

    @Override
    public void describeTo(Description description) {
    }

    @Override
    protected boolean matchesSafely(Date date) {
        return DataUtils.isMesmaData(date, DataUtils.obterDataComDiferencaDias(qtdDias));
    }
}
