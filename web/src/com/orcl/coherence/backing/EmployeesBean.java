package com.orcl.coherence.backing;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.Cluster;
import com.tangosol.net.Member;
import com.tangosol.net.NamedCache;

import com.tangosol.util.Filter;
import com.tangosol.util.filter.AlwaysFilter;
import com.tangosol.util.filter.AndFilter;
import com.tangosol.util.filter.EqualsFilter;

import com.tangosol.util.filter.LikeFilter;

import com.tangosol.util.filter.OrFilter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ArrayDataModel;

import javax.faces.model.DataModel;

import com.orcl.coherence.model.Employees;

import oracle.adf.view.faces.component.core.data.CoreColumn;
import oracle.adf.view.faces.component.core.data.CoreTable;
import oracle.adf.view.faces.component.core.data.CoreTableSelectOne;
import oracle.adf.view.faces.component.core.input.CoreInputText;
import oracle.adf.view.faces.component.core.input.CoreSelectItem;
import oracle.adf.view.faces.component.core.nav.CoreCommandButton;
import oracle.adf.view.faces.component.core.output.CoreMessages;
import oracle.adf.view.faces.component.core.output.CoreOutputText;
import oracle.adf.view.faces.component.html.HtmlBody;
import oracle.adf.view.faces.component.html.HtmlHead;
import oracle.adf.view.faces.component.html.HtmlHtml;

public class EmployeesBean {

    private NamedCache employees = CacheFactory.getCache("Employees");

    private HtmlForm form;
    private CoreTable resultTable;

    private ArrayDataModel model = null;
    private CoreInputText firstName;
    private CoreInputText lastName;
    private CoreMessages messages;

    private CoreCommandButton search;
    private CoreCommandButton loadCache;
    private CoreCommandButton seeAll;
    private CoreCommandButton salaryRaiser;
    private CoreCommandButton salaryDecreaser;

    public void setForm(HtmlForm form) {
        this.form = form;
    }

    public HtmlForm getForm() {
        return form;
    }

    public void setResultTable(CoreTable table1) {
        this.resultTable = table1;
    }

    public CoreTable getResultTable() {
        return resultTable;
    }


    public EmployeesBean() {
    }

    public void loadCache(ActionEvent actionEvent) {
        long empId;
        for (int i = 100; i < 207; i++) {
            empId = i;
            Employees emp = (Employees)employees.get(empId);
        }
        seeAll(actionEvent);
        search.setDisabled(false);
        seeAll.setDisabled(false);
        salaryRaiser.setDisabled(false);
        salaryDecreaser.setDisabled(false);
        
    }

    public void seeAll(ActionEvent actionEvent) {

        long startTime = System.currentTimeMillis();
        Set empSet = employees.entrySet();

        int size = empSet.size();
        System.out.println("size " + size);

        List list = new ArrayList();

        for (Iterator it = empSet.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry)it.next();
            Employees empl = (Employees)entry.getValue();
            list.add(empl);
        }
        model = new ArrayDataModel(list.toArray());

        long endTime = System.currentTimeMillis();

        addMessage(startTime,endTime,"done");
    }

    public void search(ActionEvent actionEvent) {
        // Add event code here...
        long startTime = System.currentTimeMillis();
        
        Filter filter = null;
        if ( firstName.getValue() != null && lastName.getValue() != null ) {
          filter = new OrFilter( new LikeFilter("getFirstName", firstName.getValue().toString())
                               , new LikeFilter("getLastName" , lastName.getValue().toString()));
        } else if  ( firstName.getValue() != null && lastName.getValue() == null ) {
            filter = new LikeFilter("getFirstName", firstName.getValue().toString());
        } else if  ( firstName.getValue() == null && lastName.getValue() != null ) {
            filter = new LikeFilter("getLastName", lastName.getValue().toString());
        } else {
            seeAll(actionEvent);
            return;
        }
        
        Set empSet = employees.entrySet(filter);

        int size = empSet.size();
        List list = new ArrayList();

        for (Iterator it = empSet.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry)it.next();
            Employees empl = (Employees)entry.getValue();
            list.add(empl);
        }
        model = new ArrayDataModel(list.toArray());

        long endTime = System.currentTimeMillis();

        addMessage(startTime,endTime,"done");

    }

    private void addMessage(Long start,Long end,String text) {
        FacesMessage curentMessage = new FacesMessage(text, text);
        curentMessage.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage("messages", 
                                                     curentMessage);

        String message = 
            "Total time taken is " + (end - start) / 1000F + 
            " seconds";
        curentMessage = new FacesMessage(message, message);
        curentMessage.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage("messages", 
                                                     curentMessage);


        Cluster cluster = CacheFactory.getCluster();
        Member memberThis = cluster.getLocalMember();
        // list all the members - highlighting the curret one
        for (Iterator iter = cluster.getMemberSet().iterator(); iter.hasNext(); 
        ) {
            Member member = (Member)iter.next();
            String msg = 
                member.toString() + (member.equals(memberThis) ? " <-- this node" : 
                                     "");
            curentMessage = new FacesMessage(msg, msg);
            curentMessage.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage("messages", 
                                                         curentMessage);
        }


    }

    public void raiseSalary(ActionEvent actionEvent) {
        long startTime = System.currentTimeMillis();
        employees.invokeAll(AlwaysFilter.INSTANCE, new RaiseSalary());
        long endTime = System.currentTimeMillis();
        addMessage(startTime,endTime,"done");
    }

    public void lowerSalary(ActionEvent actionEvent) {
        long startTime = System.currentTimeMillis();
        employees.invokeAll(AlwaysFilter.INSTANCE, new LowerSalary());
        long endTime = System.currentTimeMillis();
        addMessage(startTime,endTime,"done");
    }


    public DataModel getEmployees() {
        return model;
    }


    public void setFirstName(CoreInputText inputText1) {
        this.firstName = inputText1;
    }

    public CoreInputText getFirstName() {
        return firstName;
    }

    public void setLastName(CoreInputText inputText2) {
        this.lastName = inputText2;
    }

    public CoreInputText getLastName() {
        return lastName;
    }

    public void setSearch(CoreCommandButton commandButton1) {
        this.search = commandButton1;
    }

    public CoreCommandButton getSearch() {
        return search;
    }

    public void setMessages(CoreMessages messages1) {
        this.messages = messages1;
    }

    public CoreMessages getMessages() {
        return messages;
    }



    public void setLoadCache(CoreCommandButton commandButton1) {
        this.loadCache = commandButton1;
    }

    public CoreCommandButton getLoadCache() {
        return loadCache;
    }


    public void setSalaryRaiser(CoreCommandButton commandButton1) {
        this.salaryRaiser = commandButton1;
    }

    public CoreCommandButton getSalaryRaiser() {
        return salaryRaiser;
    }


    public void setSalaryDecreaser(CoreCommandButton commandButton1) {
        this.salaryDecreaser = commandButton1;
    }

    public CoreCommandButton getSalaryDecreaser() {
        return salaryDecreaser;
    }


    public void setSeeAll(CoreCommandButton seeAll) {
        this.seeAll = seeAll;
    }

    public CoreCommandButton getSeeAll() {
        return seeAll;
    }
}
