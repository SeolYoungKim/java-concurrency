package thread_safety;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 자바 병렬 프로그래밍 02장에 사용된 예제
 */
public class StatelessFactorizer implements Servlet {
    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);
        if (i.equals(lastNumber.get())) {
            encodeIntoResponse(servletResponse, lastFactors.get());
        } else {
            BigInteger[] factors = factor(i);
            lastNumber.set(i);
            lastFactors.set(factors);
            encodeIntoResponse(servletResponse, factors);
        }
    }

    private BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");  // 예제 편의를 위한 코드 😅
    }

    private BigInteger[] factor(BigInteger i) {
        // 대충 인수 분해를 하는 메서드
        return new BigInteger[0];
    }

    private void encodeIntoResponse(ServletResponse servletResponse, BigInteger[] factors) {

    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
