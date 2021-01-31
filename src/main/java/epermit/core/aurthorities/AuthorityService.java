package epermit.core.aurthorities;

import java.util.List;

import epermit.common.CommandResult;

public interface AuthorityService {
    List<AuthorityDto> getAll();
    AuthorityDto getByCode(String code);
    CommandResult create(CreateAuthorityInput input);
    CommandResult createKey(CreateAuthorityKeyInput input);
    CommandResult createQuota(CreateAuthorityQuotaInput input);
    CommandResult revokeKey(int id);
    CommandResult revokeQuota(int id);
    CommandResult setClaimsRule(SetClaimsRuleInput input);
}
