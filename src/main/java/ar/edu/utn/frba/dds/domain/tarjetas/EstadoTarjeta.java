package ar.edu.utn.frba.dds.domain.tarjetas;

public enum EstadoTarjeta {
    Autorizada, //el colaborador ya la recicio y autorizo
    Enviada, //la tarjeta fue enviada al colaborador, ya no esta en manos de la ong
    Desautorizada, //la tarjeta fue desautorizada por el colaborador o por algun otro motivo
    Asignada //cuando el colab recien se registra ya se le asigna un numero
}
