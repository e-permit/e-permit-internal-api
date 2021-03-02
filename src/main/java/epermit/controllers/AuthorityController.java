package epermit.controllers;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import an.awesome.pipelinr.Pipeline;
import epermit.commands.CreateAuthorityCommand;
import epermit.commands.CreateQuotaCommand;
import epermit.common.CommandResult;
import epermit.data.entities.Authority;
import epermit.data.repositories.AuthorityRepository;
import epermit.dtos.AuthorityDto;

@RestController
@RequestMapping("/authorities")
public class AuthorityController {
    private final Pipeline pipeline;
    private final AuthorityRepository authorityRepository;
    private final ModelMapper modelMapper;

    public AuthorityController(Pipeline pipeline, AuthorityRepository authorityRepository,
            ModelMapper modelMapper) {
        this.authorityRepository = authorityRepository;
        this.pipeline = pipeline;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<AuthorityDto> getAll() {
        List<epermit.data.entities.Authority> all = authorityRepository.findAll();
        return all.stream().map(x -> modelMapper.map(x, AuthorityDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{code}")
    public AuthorityDto getByCode(String code) {
        Authority authority = authorityRepository.findByCode(code).get();
        return modelMapper.map(authority, AuthorityDto.class);
    }

    @PostMapping()
    public CommandResult create(CreateAuthorityCommand command) {
        return command.execute(pipeline);
    }

    @PostMapping("/{code}/createquota")
    public CommandResult createQuota(CreateQuotaCommand command) {
        return command.execute(pipeline);
    }

}



/*
 * @PostMapping(value = "/token") public ResponseEntity<String> post(@RequestBody ApiClientInput
 * input) { try { ApiClientValidationResult r = apiClientService.validate(input); if (!r.isValid())
 * {
 * 
 * } return new ResponseEntity<>("doc.getId()", HttpStatus.OK);
 * 
 * } catch (Exception exception) { logger.error(exception.getMessage(), exception); return new
 * ResponseEntity<>(null, HttpStatus.BAD_REQUEST); } }
 */
