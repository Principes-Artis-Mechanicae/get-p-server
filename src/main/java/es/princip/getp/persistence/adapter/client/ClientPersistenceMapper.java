package es.princip.getp.persistence.adapter.client;

import es.princip.getp.domain.client.model.Address;
import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.common.model.BankAccount;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface ClientPersistenceMapper {

    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "memberId", target = "memberId.value")
    @Mapping(source = "email", target = "email.value")
    Client mapToDomain(ClientJpaEntity clientJpaEntity);

    @InheritInverseConfiguration
    ClientJpaEntity mapToJpa(Client client);

    Address mapToDomain(AddressJpaVO addressJpaVO);

    BankAccount mapToDomain(BankAccountJpaVO bankAccountJpaVO);
}
