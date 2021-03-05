package epermit.data.services;

import epermit.common.CommandResult;
import epermit.data.repositories.ReceivedMessageRepository;
import lombok.SneakyThrows;

/*public class MessageServiceImpl implements MessageService {
    private final ReceivedMessageRepository receivedMessageRepository;

    public MessageServiceImpl(ReceivedMessageRepository receivedMessageRepository) {
        this.receivedMessageRepository = receivedMessageRepository;
    }

    @Override
    @SneakyThrows
    public CommandResult receive(ReceiveMessageInput input) {
        return CommandResult.success();
        /*
         * JWSObject jwsObject = JWSObject.parse(input.getJws()); JWSHeader header =
         * jwsObject.getHeader(); Map<String, Object> claims =
         * jwsObject.getPayload().toJSONObject(); String iss = claims.get("iss").toString(); String
         * aud = claims.get("aud").toString(); if(!properties.getIssuer().getCode().equals(aud)){
         * return CommandResult.fail("INVALID_AUD", "The aud is not matched"); } Optional<Authority>
         * authority = authorityRepository.findByCode(iss); if (!authority.isPresent()) { return
         * CommandResult.fail("ISS_NOTFOUND", "The issuer is not found"); } Optional<AuthorityKey>
         * key = authority.get().getKeys().stream() .filter(x ->
         * x.getKid().equals(header.getKeyID())).findFirst();
         * 
         * ECPublicKey ecPublicKey = ECKey.parse(key.get().getContent()).toECPublicKey();
         * JWSVerifier verifier = new ECDSAVerifier(ecPublicKey); Boolean r =
         * jwsObject.verify(verifier); if (!r) { return CommandResult.fail("INVALID_SIG",
         * "The signature is invalid"); } int pmt = (int) claims.get("pmt"); if (pmt == 1) { return
         * messageUtils.handleCreate(claims); } else if (pmt == 2) { return
         * messageUtils.handleRevoke(claims); } else if (pmt == 3) { return
         * messageUtils.handleFeedback(claims); } return CommandResult.fail("INVALID_PMT",
         * "The permit message type is invalid");
         */
    /*}
}*/
