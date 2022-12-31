package com.example.compile.iteration.analysis;

import java.util.ArrayList;
import java.util.List;

public class Statement {
    public int layer;
    public String type;
    public String name;
    public String initValue;
    public String statementType;
    public List<Statement> subStatementList = new ArrayList<>();
    public List<Statement> formalParameterList = new ArrayList<>();
    public List<Statement> realParameterList = new ArrayList<>();

    public List<Statement> getFormalParameterList() {
        return formalParameterList;
    }

    public void setFormalParameterList(List<Statement> formalParameterList) {
        this.formalParameterList = formalParameterList;
    }

    public List<Statement> getRealParameterList() {
        return realParameterList;
    }

    public void setRealParameterList(List<Statement> realParameterList) {
        this.realParameterList = realParameterList;
    }


    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public List<Statement> getSubStatementList() {
        return subStatementList;
    }

    public void setSubStatementList(List<Statement> statementList) {
        this.subStatementList = statementList;
    }

    public String getStatementType() {
        return statementType;
    }

    public void setStatementType(String statementType) {
        this.statementType = statementType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitValue() {
        return initValue;
    }

    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }
}
