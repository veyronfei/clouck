package com.clouck.application;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.clouck.comparator.Ec2Comparator;
import com.clouck.exception.CloudVersionEc2CompparatorNotFoundException;
import com.clouck.exception.CloudVersionIllegalStateException;
import com.clouck.model.Account;
import com.clouck.model.aws.AbstractResource;
import com.clouck.repository.AccountDao;
import com.google.common.base.Optional;

@Component
public class SystemCache {
    private static final Logger log = LoggerFactory.getLogger(SystemCache.class);

    @Autowired
    private ApplicationContext context;

    // user dao instead of service to avoid circular reference
    @Autowired
    private AccountDao accountDao;

    private Map<Class<? extends AbstractResource<?>>, Ec2Comparator<?>> ec2Resources = new HashMap<>();
    private String demoAccountId;

    public String findBySysKey(SysKey key) {
        return "";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Ec2Comparator findComparator(Class<? extends AbstractResource> resourceClass) {
        if (ec2Resources.values().isEmpty()) {
            Map<String, Ec2Comparator> comparatorMaps = context.getBeansOfType(Ec2Comparator.class);
            for (Ec2Comparator comparator : comparatorMaps.values()) {
                ec2Resources.put(comparator.getType(), comparator);
            }
        }
        Ec2Comparator ec2Comparator = ec2Resources.get(resourceClass);
        if (ec2Comparator == null) {
            throw new CloudVersionEc2CompparatorNotFoundException(resourceClass.getSimpleName());
        } else {
            return ec2Comparator;
        }
    }

    public String findDemoAccountId() {
        if (demoAccountId == null) {
            Optional<Account> oAccount = accountDao.findDemoAccount();
            if (oAccount.isPresent()) {
                demoAccountId = oAccount.get().getId();
            } else {
                throw new CloudVersionIllegalStateException("demo account is not loaded. should load demo account when app start up.");
            }
        }
        return demoAccountId;
    }
}
