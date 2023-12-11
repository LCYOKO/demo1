package com.xiaomi.demo.document.report.word.poi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.StringJoiner;

/**
 * @Author:柳维民
 * @Date: 2023/10/10 16:27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TextInfo {

    /**
     * 段落
     */
    private List<String> phases;


    /**
     * 下划线
     */
    private List<String> underlineContent;


    /**
     *
     */
    String getInfoFromUnderlineContent(int[] underlineLocation) {
        return this.extractInfo(underlineLocation, this.underlineContent);
    }

    public String getInfoFromPhases(int[] phaseLocations) {
        return this.extractInfo(phaseLocations, this.phases);
    }

    public String extractInfo(int[] locations, List<String> contents) {
        if (locations != null) {
            StringJoiner stringJoiner = new StringJoiner("\n");
            for (int location : locations) {
                if (location < contents.size()) {
                    if (location == -1) {
                        stringJoiner.add(contents.get(contents.size() - 1));
                    } else {
                        stringJoiner.add(contents.get(location));
                    }
                }
            }
            return stringJoiner.toString().replaceAll(" ", "");
        }
        return "";
    }

    public String getInfoStartWithNum() {
        if (CollectionUtils.isNotEmpty(this.phases)) {
            StringJoiner stringJoiner = new StringJoiner(",");
            for (String phase : phases) {
                char firstChar = phase.charAt(0);
                if (Character.isDigit(firstChar)) {
                    stringJoiner.add(phase);
                }
            }
            return stringJoiner.toString().replaceAll(" ", "");
        }
        return "";
    }
}
