package com.octest.dao;

import java.util.List;

import com.octest.beans.Equipe;

public interface EquipeDao {

	void ajouter(Equipe equipe) throws DaoException;

	List<Equipe> listerEquipes() throws DaoException;
}
