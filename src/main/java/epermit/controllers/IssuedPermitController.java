package epermit.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import an.awesome.pipelinr.Pipeline;
import epermit.commands.CreatePermitCommand;
import epermit.commands.RevokePermitCommand;
import epermit.common.CommandResult;
import epermit.core.issuedcredentials.CreateIssuedCredentialInput;
import epermit.core.issuedcredentials.IssuedCredentialDto;
import epermit.data.repositories.IssuedPermitRepository;

@RestController
@RequestMapping("/issued_permits")
public class IssuedPermitController {

    private final IssuedPermitRepository repository;
    private final ModelMapper modelMapper;
    private final Pipeline pipeline;

    public IssuedPermitController(IssuedPermitRepository repository, ModelMapper modelMapper, Pipeline pipeline) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.pipeline = pipeline;
    }

    @GetMapping()
    public ResponseEntity<Page<IssuedCredentialDto>> getAll(Pageable pageable) {
        Page<epermit.data.entities.IssuedPermit> entities = repository.findAll(pageable);
        Page<IssuedCredentialDto> dtoPage =
                entities.map(x -> modelMapper.map(x, IssuedCredentialDto.class));
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public IssuedCredentialDto getById(Long id) {
        IssuedCredentialDto dto =
                modelMapper.map(repository.findById(id).get(), IssuedCredentialDto.class);
        return dto;
    }

    @GetMapping("/find/{serialNumber}")
    public IssuedCredentialDto getBySerialNumber(String serialNumber) {
        IssuedCredentialDto dto =
                modelMapper.map(repository.findOneBySerialNumber(serialNumber).get(), IssuedCredentialDto.class);
        return dto;
    }

    @PostMapping()
    public CommandResult post(@RequestBody CreatePermitCommand cmd) {
        return cmd.execute(pipeline);
    }

    /*
     * @PatchMapping("/{id}/send") public CommandResult send(Long id) { return
     * credentialService.send(id); }
     */

    @PatchMapping("/{id}/revoke")
    public CommandResult revoke(Long id) {
        RevokePermitCommand cmd = new RevokePermitCommand();
        cmd.setId(id);
        return cmd.execute(pipeline);
    }

    // from verifier
    /*
     * @PatchMapping("/{id}/setused") public CommandResult setUsed(Long id) { return
     * credentialService.setUsed(id, ""); }
     */

}
