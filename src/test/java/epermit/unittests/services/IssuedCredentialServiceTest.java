package epermit.unittests.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import epermit.data.entities.IssuedCredential;
import epermit.data.repositories.IssuedCredentialRepository;
import epermit.data.services.IssuedCredentialServiceImpl;

@ExtendWith(MockitoExtension.class)
public class IssuedCredentialServiceTest {

    @Mock
    IssuedCredentialRepository repository;

    @Test
    public void greetingShouldReturnMessageFromService() {
        List<IssuedCredential> crList = new ArrayList<>();
        for (long i = 0; i < 10; i++) {
            IssuedCredential cr = new IssuedCredential();
            cr.setId(i);
            cr.setCid("123456");
            cr.setClaims("claims");
            cr.setExp((long) 123);
            cr.setIat((long) 123);
            cr.setJws("claims");
            cr.setPid(123);
            cr.setPt(1);
            cr.setPy(2021);
            cr.setQrcode("claims");
            cr.setSub("claims");
            cr.setRevoked(false);
            cr.setSerialNumber("claims");
            cr.setUsed(false);
            crList.add(cr);
        }
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id").descending());
        Page<IssuedCredential> foundPage = new PageImpl<>(crList, pageable, (long)20);
        when(repository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(foundPage);
        IssuedCredentialServiceImpl service = new IssuedCredentialServiceImpl(repository, new ModelMapper());
        Page<epermit.core.issuedcredentials.IssuedCredentialDto> r = service.getAll(pageable);
        assertEquals(r.getContent().size(), 10);
        assertEquals(r.getTotalElements(), 20);
    }
}
