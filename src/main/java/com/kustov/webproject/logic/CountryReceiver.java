package com.kustov.webproject.logic;

import com.kustov.webproject.dao.CountryDAO;
import com.kustov.webproject.entity.Country;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;

import java.util.List;

public class CountryReceiver extends DefaultReceiver{
    public List<Country> findCountries() throws ServiceException{
        CountryDAO dao = new CountryDAO();
        try {
            return dao.findAll();
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }
}
