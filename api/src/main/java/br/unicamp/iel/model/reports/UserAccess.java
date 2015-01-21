package br.unicamp.iel.model.reports;

import java.math.BigInteger;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserAccess {
	private Date primeiro; 
	private Date ultimo;
	private String atividade;
	private BigInteger total; 
}
