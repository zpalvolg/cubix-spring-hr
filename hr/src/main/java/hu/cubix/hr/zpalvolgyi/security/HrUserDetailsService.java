package hu.cubix.hr.zpalvolgyi.security;

import java.util.Arrays;

import hu.cubix.hr.zpalvolgyi.model.Employee;
import hu.cubix.hr.zpalvolgyi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class HrUserDetailsService implements UserDetailsService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee emp = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new HrUser(username, emp.getPassword(), Arrays.asList(new SimpleGrantedAuthority("USER")), emp);
    }

}
