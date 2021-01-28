package epermit.core.aurthorities;

import java.util.List;

public interface AuthorityService {
    List<Authority> getAll();
    Authority getByCode(String code);
    CreateResult create(CreateInput input);
    CreateKeyResult createKey(CreateKeyInput input);
    CreateQuotaResult createQuota(CreateQuotaInput input);
    RevokeKeyResult revokeKey(RevokeKeyInput input);
    RevokeQuotaResult revokeQuota(RevokeQuotaInput input);
    SetClaimsRuleResult setClaimsRule(SetClaimsRuleInput input);
}
