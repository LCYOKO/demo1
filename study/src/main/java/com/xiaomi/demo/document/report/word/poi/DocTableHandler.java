package com.xiaomi.demo.document.report.word.poi;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author:柳维民
 * @Date: 2023/9/26 15:36
 */
public class DocTableHandler implements ValueHandler {
    public static final String TEXT_TAG = "w:t";

    @Override
    public String getTargetValueFromSource(DocxPosition docxPosition, XWPFDocument document) {
        String tableName = docxPosition.tableName();
        List<XWPFTable> tables = document.getTables();
        XWPFTable table = this.findTable(tables, tableName);
        if (Objects.nonNull(table)) {
            int rowNum = docxPosition.rowNum();
            int column = docxPosition.column();
            XWPFTableRow row = table.getRow(rowNum);
            if (Objects.nonNull(row)) {
                XWPFTableCell cell = row.getCell(column);
                if (Objects.nonNull(cell)) {
                    return cell.getText();
                }
            }
            return "";
        }
        return null;
    }


    private XWPFTable findTable(List<XWPFTable> tables, String targetTableName) {

        if (CollectionUtils.isNotEmpty(tables)) {
            for (XWPFTable table : tables) {
                CTTbl ctTbl = table.getCTTbl();
                Node domNode = ctTbl.getDomNode();


                Node tableNameNode = domNode.getPreviousSibling().getLastChild();
                AtomicReference<String> reference = new AtomicReference<>("");
                findTableName(tableNameNode, reference);
                String tableName = reference.get();
                if (targetTableName.equals(tableName)) {
                    return table;
                }
            }
        }
        return null;
    }

    private void findTableName(Node pNode, AtomicReference<String> reference) {

        if (TEXT_TAG.equals(pNode.getNodeName())) {
            reference.set(pNode.getFirstChild().getNodeValue());
        }

        if (pNode.hasChildNodes()) {
            NodeList childNodes = pNode.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (StringUtils.isNotBlank(reference.get())) {
                    break;
                }
                findTableName(item, reference);
            }
        }
    }
}
