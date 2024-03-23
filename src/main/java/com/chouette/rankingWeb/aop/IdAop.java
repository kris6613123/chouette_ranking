package com.chouette.rankingWeb.aop;

import com.chouette.rankingWeb.exception.CustomPageUnavailableException;
import com.chouette.rankingWeb.service.*;
import com.chouette.rankingWeb.vo.BranchVO;
import com.chouette.rankingWeb.vo.CustomerVO;
import com.chouette.rankingWeb.vo.GameVO;
import com.chouette.rankingWeb.vo.NewsVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class IdAop {
    private final BranchService branchService;
    private final CustomerService customerService;
    private final GameService gameService;
    private final NewsService newsService;

    @Pointcut( "execution(* com.chouette.rankingWeb.controller.CtrlBranch.mod(..)))" )
    private void checkBranch() {}

    @Pointcut( "execution(* com.chouette.rankingWeb.controller.CtrlCustomer.mod(..))" ) // +
            //"execution(* com.chouette.rankingWeb.controller.CtrlCustomer.view(..)) ||" +
            //"execution(* com.chouette.rankingWeb.controller.CtrlCustomer.chartData(..))" )
    private void checkCustomer() {}

    @Pointcut( "execution(* com.chouette.rankingWeb.controller.CtrlCustomer.view(..)) ||" +
            "execution(* com.chouette.rankingWeb.controller.CtrlCustomer.chartData(..))" )
    private void checkCustomerHaveGame() {}

    @Pointcut( "execution(* com.chouette.rankingWeb.controller.CtrlGame.mod(..)) )")
    private void checkGame() {}

    @Pointcut( "execution(* com.chouette.rankingWeb.controller.CtrlNews.mod(..)) || execution(* com.chouette.rankingWeb.controller.CtrlNews.view(..)))")
    private void checkNews() {}

    @Before("checkBranch()")
    public void checkAccessBranch( final JoinPoint joinPoint ) throws CustomPageUnavailableException {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] arguments = joinPoint.getArgs();

        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals("id")) {
                Integer id = (Integer) arguments[i];
                if ( id != null ) {
                    if ( branchService.getItem( new BranchVO( id ) ) == null ) {
                        throw new CustomPageUnavailableException("redirect:/branch/list");
                    }
                }
            }
        }
    }

    @Before("checkCustomer()")
    public void checkAccessCustomer( final JoinPoint joinPoint ) throws CustomPageUnavailableException {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] arguments = joinPoint.getArgs();

        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals("id")) {
                Integer id = (Integer) arguments[i];
                if ( id != null ) {
                    log.info("checkCustomer id is " + id);
                    if ( customerService.getItem( new CustomerVO( id ) ) == null ) {
                        log.info("checkCustomer back  " + id);
                        throw new CustomPageUnavailableException("redirect:/search");
                    }
                }
            }
        }
    }

    @Before("checkCustomerHaveGame()")
    public void checkAccessCustomerHaveGame( final JoinPoint joinPoint ) throws CustomPageUnavailableException {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] arguments = joinPoint.getArgs();

        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals("id")) {
                Integer id = (Integer) arguments[i];
                if ( id != null ) {
                    log.info("customerHaveGame id is " + id);
                    if ( gameService.getListByCustomer( id ).size() == 0 ) {
                        log.info("customerHaveGame back");
                        throw new CustomPageUnavailableException("redirect:/search");
                    }
                }
            }
        }
    }

    @Before("checkGame()")
    public void checkAccessGame( final JoinPoint joinPoint ) throws CustomPageUnavailableException {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] arguments = joinPoint.getArgs();

        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals("id")) {
                Integer id = (Integer) arguments[i];
                if ( id != null ) {
                    if ( gameService.getItem( new GameVO( id ) ) == null ) {
                        throw new CustomPageUnavailableException("redirect:/game/list");
                    }
                }
            }
        }
    }

    @Before("checkNews()")
    public void checkAccessNews( final JoinPoint joinPoint ) throws CustomPageUnavailableException {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] arguments = joinPoint.getArgs();

        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals("id")) {
                Integer id = (Integer) arguments[i];
                if ( id != null ) {
                    if ( newsService.getItem( new NewsVO( id ) ) == null ) {
                        throw new CustomPageUnavailableException("redirect:/news");
                    }
                }
            }
        }
    }
}