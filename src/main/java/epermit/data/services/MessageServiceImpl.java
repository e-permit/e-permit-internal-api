package epermit.data.services;

import epermit.common.CommandResult;
import epermit.core.messages.MessageInput;
import epermit.core.messages.MessageService;
import epermit.data.entities.Authority;
import epermit.data.entities.AuthorityKey;
import epermit.data.repositories.AuthorityRepository;
import epermit.data.utils.MessageUtils;
import lombok.SneakyThrows;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.jwk.*;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.security.interfaces.ECPublicKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MessageServiceImpl implements MessageService {

    private final MessageUtils messageUtils;
    private final AuthorityRepository authorityRepository;

    public MessageServiceImpl(MessageUtils messageUtils, AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
        this.messageUtils = messageUtils;
    }

    @Override
    @SneakyThrows
    public CommandResult create(MessageInput input) {
        JWSObject jwsObject = JWSObject.parse(input.getJws());
        JWSHeader header = jwsObject.getHeader();
        Map<String, Object> claims = jwsObject.getPayload().toJSONObject();
        String iss = claims.get("iss").toString();
        Optional<Authority> authority = authorityRepository.findByCode(iss);
        if (!authority.isPresent()) {
            return CommandResult.fail("ISS_NOTFOUND", "The issuer is not found");
        }
        Optional<AuthorityKey> key = authority.get().getKeys().stream()
                .filter(x -> x.getKid().equals(header.getKeyID())).findFirst();

        ECPublicKey ecPublicKey = ECKey.parse(key.get().getContent()).toECPublicKey();
        JWSVerifier verifier = new ECDSAVerifier(ecPublicKey);
        Boolean r = jwsObject.verify(verifier);
        if (!r) {
            return CommandResult.fail("INVALID_SIG", "The signature is invalid");
        }
        int pmt = (int)claims.get("pmt");
        if(pmt == 1){
            return messageUtils.handleCreate(claims);
        }else if(pmt == 2){
            return messageUtils.handleRevoke(claims);
        }else if(pmt == 3){
            return messageUtils.handleFeedback(claims);
        }
        return CommandResult.fail("INVALID_PMT", "The permit message type is invalid");
    }
}
