/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.swing.laf.flatlaf;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.util.SystemInfo;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;
import javax.swing.UIManager;
import org.netbeans.api.actions.Editable;
import org.netbeans.api.actions.Openable;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.LifecycleManager;
import org.openide.awt.Notification;
import org.openide.awt.NotificationDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/**
 * @author Karl Tauber
 */
@OptionsPanelController.Keywords(
    keywords={"#KW_FlatLafOptions"},
    location="Appearance",
    tabTitle="#FlatLaf_DisplayName"
)
public class FlatLafOptionsPanel extends javax.swing.JPanel {

    private static final Color DEFAULT = new Color(0, true);
    private static final Color currentAccentColor = getPrefsAccentColorOrDefault();
    private static final boolean currentUseWindowDecorations = FlatLafPrefs.isUseWindowDecorations();

    private static final RequestProcessor RP = new RequestProcessor(FlatLafOptionsPanel.class);

    private final FlatLafOptionsPanelController controller;

    /**
     * Creates new form FlatLafOptionsPanel
     */
    public FlatLafOptionsPanel(FlatLafOptionsPanelController controller) {
        this.controller = controller;
        initComponents();
        initAccentColor();
        updateEnabled();
    }

    private void initAccentColor() {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Color> colors = new ArrayList<>();
        names.add("default");
        colors.add(DEFAULT);

        String s = UIManager.getString("nb.accentColors.predefined");
        if (s == null) {
            // FlatLaf is not the current look and feel
            Properties properties = new Properties();
            try {
                properties.load(getClass().getClassLoader().getResourceAsStream(
                        "org/netbeans/swing/laf/flatlaf/FlatLaf.properties"));
                s = properties.getProperty("nb.accentColors.predefined");
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        if (s != null) {
            for (String part : s.split(";")) {
                int sepIndex = part.indexOf(':');
                if (sepIndex >= 1) {
                    String name = part.substring(0, sepIndex).trim();
                    String value = part.substring(sepIndex + 1).trim();
                    if (!name.isEmpty() && !value.isEmpty()) {
                        Color color = FlatLafPrefs.parseColor(value);
                        if (color != null) {
                            names.add(name);
                            colors.add(color);
                        }
                    }
                }
            }
        }

        accentColorField.setModel(colors.toArray(new Color[0]), names.toArray(new String[0]));
    }

    private void updateEnabled() {
        boolean supportsWindowDecorations = FlatLaf.supportsNativeWindowDecorations() || new FlatLightLaf().getSupportsWindowDecorations();
        useWindowDecorationsCheckBox.setEnabled(supportsWindowDecorations);
        unifiedTitleBarCheckBox.setEnabled(supportsWindowDecorations && useWindowDecorationsCheckBox.isSelected());
        menuBarEmbeddedCheckBox.setEnabled(supportsWindowDecorations && useWindowDecorationsCheckBox.isSelected());

        underlineMenuSelectionCheckBox.setEnabled(!SystemInfo.isMacOS);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        accentColorLabel = new javax.swing.JLabel();
        accentColorField = new org.openide.awt.ColorComboBox();
        needsRestartLabel = new javax.swing.JLabel();
        useWindowDecorationsCheckBox = new javax.swing.JCheckBox();
        menuBarEmbeddedCheckBox = new javax.swing.JCheckBox();
        unifiedTitleBarCheckBox = new javax.swing.JCheckBox();
        underlineMenuSelectionCheckBox = new javax.swing.JCheckBox();
        alwaysShowMnemonicsCheckBox = new javax.swing.JCheckBox();
        advPanel = new javax.swing.JPanel();
        customPropertiesLabel = new javax.swing.JLabel();
        customPropertiesButton = new javax.swing.JButton();
        showFileChooserFavoritesCheckBox = new javax.swing.JCheckBox();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        org.openide.awt.Mnemonics.setLocalizedText(accentColorLabel, org.openide.util.NbBundle.getMessage(FlatLafOptionsPanel.class, "FlatLafOptionsPanel.accentColorLabel.text")); // NOI18N

        accentColorField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accentColorFieldActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(needsRestartLabel, org.openide.util.NbBundle.getMessage(FlatLafOptionsPanel.class, "FlatLafOptionsPanel.needsRestartLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(useWindowDecorationsCheckBox, org.openide.util.NbBundle.getMessage(FlatLafOptionsPanel.class, "FlatLafOptionsPanel.useWindowDecorationsCheckBox.text")); // NOI18N
        useWindowDecorationsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useWindowDecorationsCheckBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(menuBarEmbeddedCheckBox, org.openide.util.NbBundle.getMessage(FlatLafOptionsPanel.class, "FlatLafOptionsPanel.menuBarEmbeddedCheckBox.text")); // NOI18N
        menuBarEmbeddedCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBarEmbeddedCheckBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(unifiedTitleBarCheckBox, org.openide.util.NbBundle.getMessage(FlatLafOptionsPanel.class, "FlatLafOptionsPanel.unifiedTitleBarCheckBox.text")); // NOI18N
        unifiedTitleBarCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unifiedTitleBarCheckBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(underlineMenuSelectionCheckBox, org.openide.util.NbBundle.getMessage(FlatLafOptionsPanel.class, "FlatLafOptionsPanel.underlineMenuSelectionCheckBox.text")); // NOI18N
        underlineMenuSelectionCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                underlineMenuSelectionCheckBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(alwaysShowMnemonicsCheckBox, org.openide.util.NbBundle.getMessage(FlatLafOptionsPanel.class, "FlatLafOptionsPanel.alwaysShowMnemonicsCheckBox.text")); // NOI18N
        alwaysShowMnemonicsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alwaysShowMnemonicsCheckBoxActionPerformed(evt);
            }
        });

        advPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(FlatLafOptionsPanel.class, "FlatLafOptionsPanel.Advanced.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(customPropertiesLabel, org.openide.util.NbBundle.getMessage(FlatLafOptionsPanel.class, "FlatLafOptionsPanel.customPropertiesLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(customPropertiesButton, org.openide.util.NbBundle.getMessage(FlatLafOptionsPanel.class, "FlatLafOptionsPanel.customPropertiesButton.text")); // NOI18N
        customPropertiesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customPropertiesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout advPanelLayout = new javax.swing.GroupLayout(advPanel);
        advPanel.setLayout(advPanelLayout);
        advPanelLayout.setHorizontalGroup(
            advPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(advPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(advPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customPropertiesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 368, Short.MAX_VALUE)
                    .addGroup(advPanelLayout.createSequentialGroup()
                        .addComponent(customPropertiesButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        advPanelLayout.setVerticalGroup(
            advPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(advPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customPropertiesLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(customPropertiesButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.openide.awt.Mnemonics.setLocalizedText(showFileChooserFavoritesCheckBox, org.openide.util.NbBundle.getMessage(FlatLafOptionsPanel.class, "FlatLafOptionsPanel.showFileChooserFavoritesCheckBox.text")); // NOI18N
        showFileChooserFavoritesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showFileChooserFavoritesCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(advPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(useWindowDecorationsCheckBox)
                    .addComponent(unifiedTitleBarCheckBox)
                    .addComponent(menuBarEmbeddedCheckBox)
                    .addComponent(underlineMenuSelectionCheckBox)
                    .addComponent(alwaysShowMnemonicsCheckBox)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(accentColorLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(accentColorField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(needsRestartLabel))
                    .addComponent(showFileChooserFavoritesCheckBox))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accentColorField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(needsRestartLabel)
                    .addComponent(accentColorLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(useWindowDecorationsCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(unifiedTitleBarCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuBarEmbeddedCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(underlineMenuSelectionCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(alwaysShowMnemonicsCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showFileChooserFavoritesCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(advPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void useWindowDecorationsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useWindowDecorationsCheckBoxActionPerformed
        updateEnabled();
        fireChanged();
    }//GEN-LAST:event_useWindowDecorationsCheckBoxActionPerformed

    private void unifiedTitleBarCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unifiedTitleBarCheckBoxActionPerformed
        fireChanged();
    }//GEN-LAST:event_unifiedTitleBarCheckBoxActionPerformed

    private void menuBarEmbeddedCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBarEmbeddedCheckBoxActionPerformed
        fireChanged();
    }//GEN-LAST:event_menuBarEmbeddedCheckBoxActionPerformed

    private void underlineMenuSelectionCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_underlineMenuSelectionCheckBoxActionPerformed
        fireChanged();
    }//GEN-LAST:event_underlineMenuSelectionCheckBoxActionPerformed

    private void alwaysShowMnemonicsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alwaysShowMnemonicsCheckBoxActionPerformed
        fireChanged();
    }//GEN-LAST:event_alwaysShowMnemonicsCheckBoxActionPerformed

    private void customPropertiesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customPropertiesButtonActionPerformed
        RP.execute(() -> {
            try {
                FileObject lafFolder = FileUtil.createFolder(FileUtil.getConfigRoot(), "LookAndFeel"); // NOI18N
                FileObject customProp = lafFolder.getFileObject("FlatLaf.properties"); // NOI18N
                if (customProp == null) {
                    customProp = lafFolder.createData("FlatLaf.properties"); // NOI18N
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(customProp.getOutputStream()))) {
                        writer.append(NbBundle.getMessage(FlatLafOptionsPanel.class,
                                "FlatLafOptionsPanel.customProperties.content")); // NOI18N
                    }
                }
                DataObject dob = DataObject.find(customProp);
                Editable editable = dob.getLookup().lookup(Editable.class);
                if (editable != null) {
                  editable.edit();
                } else {
                  // fallback to openable for platform apps without editor modules
                  Openable openable = dob.getLookup().lookup(Openable.class);
                  openable.open();
                }
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        });
    }//GEN-LAST:event_customPropertiesButtonActionPerformed

    private void accentColorFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accentColorFieldActionPerformed
        fireChanged();
    }//GEN-LAST:event_accentColorFieldActionPerformed

    private void showFileChooserFavoritesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showFileChooserFavoritesCheckBoxActionPerformed
        fireChanged();
    }//GEN-LAST:event_showFileChooserFavoritesCheckBoxActionPerformed

    private void fireChanged() {
        boolean isChanged = false;
        if(!Objects.equals(accentColorField.getSelectedColor(), getPrefsAccentColorOrDefault())
                || useWindowDecorationsCheckBox.isSelected() != FlatLafPrefs.isUseWindowDecorations()
                || unifiedTitleBarCheckBox.isSelected() != FlatLafPrefs.isUnifiedTitleBar()
                || menuBarEmbeddedCheckBox.isSelected() != FlatLafPrefs.isMenuBarEmbedded()
                || underlineMenuSelectionCheckBox.isSelected() != FlatLafPrefs.isUnderlineMenuSelection()
                || alwaysShowMnemonicsCheckBox.isSelected() != FlatLafPrefs.isAlwaysShowMnemonics()
                || showFileChooserFavoritesCheckBox.isSelected() != FlatLafPrefs.isShowFileChooserFavorites()) {
            isChanged = true;
        }
        controller.changed(isChanged);
    }

    protected void load() {
        accentColorField.setSelectedColor(getPrefsAccentColorOrDefault());
        useWindowDecorationsCheckBox.setSelected(FlatLafPrefs.isUseWindowDecorations());
        unifiedTitleBarCheckBox.setSelected(FlatLafPrefs.isUnifiedTitleBar());
        menuBarEmbeddedCheckBox.setSelected(FlatLafPrefs.isMenuBarEmbedded());
        underlineMenuSelectionCheckBox.setSelected(FlatLafPrefs.isUnderlineMenuSelection());
        alwaysShowMnemonicsCheckBox.setSelected(FlatLafPrefs.isAlwaysShowMnemonics());
        showFileChooserFavoritesCheckBox.setSelected(FlatLafPrefs.isShowFileChooserFavorites());

        updateEnabled();
    }

    protected boolean store() {
        Color accentColor = accentColorField.getSelectedColor();
        FlatLafPrefs.setAccentColor(accentColor != DEFAULT ? accentColor : null);
        FlatLafPrefs.setUseWindowDecorations(useWindowDecorationsCheckBox.isSelected());
        FlatLafPrefs.setUnifiedTitleBar(unifiedTitleBarCheckBox.isSelected());
        FlatLafPrefs.setMenuBarEmbedded(menuBarEmbeddedCheckBox.isSelected());
        FlatLafPrefs.setUnderlineMenuSelection(underlineMenuSelectionCheckBox.isSelected());
        FlatLafPrefs.setAlwaysShowMnemonics(alwaysShowMnemonicsCheckBox.isSelected());
        FlatLafPrefs.setShowFileChooserFavorites(showFileChooserFavoritesCheckBox.isSelected());

        if (!Objects.equals(accentColor, currentAccentColor)
                || SystemInfo.isLinux && useWindowDecorationsCheckBox.isSelected() != currentUseWindowDecorations) {
            askForRestart();
        }
        return false;
    }

    private static Notification restartNotification;

    private void askForRestart() {
        if(restartNotification != null) {
            restartNotification.clear();
        }
        restartNotification = NotificationDisplayer.getDefault().notify(
                NbBundle.getMessage(FlatLafOptionsPanel.class, "FlatLafOptionsPanel.restartTitle"),
                ImageUtilities.loadImageIcon( "org/netbeans/core/windows/resources/restart.png", true ), //NOI18N
                NbBundle.getMessage(FlatLafOptionsPanel.class, "FlatLafOptionsPanel.restartDetails"),
                e -> {
                    if(restartNotification != null) {
                        restartNotification.clear();
                        restartNotification = null;
                    }
                    LifecycleManager.getDefault().markForRestart();
                    LifecycleManager.getDefault().exit();
                },
                NotificationDisplayer.Priority.NORMAL, NotificationDisplayer.Category.INFO);
    }

    private static Color getPrefsAccentColorOrDefault() {
        Color accentColor = FlatLafPrefs.getAccentColor();
        return accentColor != null ? accentColor : DEFAULT;
    }

    boolean valid() {
        // TODO check whether form is consistent and complete
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.openide.awt.ColorComboBox accentColorField;
    private javax.swing.JLabel accentColorLabel;
    private javax.swing.JPanel advPanel;
    private javax.swing.JCheckBox alwaysShowMnemonicsCheckBox;
    private javax.swing.JButton customPropertiesButton;
    private javax.swing.JLabel customPropertiesLabel;
    private javax.swing.JCheckBox menuBarEmbeddedCheckBox;
    private javax.swing.JLabel needsRestartLabel;
    private javax.swing.JCheckBox showFileChooserFavoritesCheckBox;
    private javax.swing.JCheckBox underlineMenuSelectionCheckBox;
    private javax.swing.JCheckBox unifiedTitleBarCheckBox;
    private javax.swing.JCheckBox useWindowDecorationsCheckBox;
    // End of variables declaration//GEN-END:variables
}
