package ar.edu.utn.frba.dds.server.obervabilidad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TransferDTO {
	
	private String src;
	private String dst;
	private Double amount;

}
