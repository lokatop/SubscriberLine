package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Вспомогательный класс для обёртывания списка адресатов.
 * Используется для сохранения списка адресатов в XML.
 */
@XmlRootElement(name = "List")
@XmlSeeAlso({InfoModel.class})
public class ListWrapper {

    private List list;

    @XmlElement(name = "item")
    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
