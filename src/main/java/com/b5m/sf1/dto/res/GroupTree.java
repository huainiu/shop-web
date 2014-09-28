package com.b5m.sf1.dto.res;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class GroupTree implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5377400556388220222L;

	private Group group = new Group();

    private List<GroupTree> groupTree = new ArrayList<GroupTree>();
    
    public void addSub(GroupTree groupTree) {
        this.groupTree.add(groupTree);
    }
    
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<GroupTree> getGroupTree() {
        return groupTree;
    }

    public void setGroupTree(List<GroupTree> groupTree) {
        this.groupTree = groupTree;
    }
    
    public GroupTree deepClone() throws RuntimeException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo;
        try {
            oo = new ObjectOutputStream(bo);
            oo.writeObject(this);
            ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream oi = new ObjectInputStream(bi);
            return (GroupTree) (oi.readObject());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this) + "\n";
    }
}
