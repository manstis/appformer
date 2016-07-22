/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.livespark.formmodeler.renderer.backend.service.impl.fieldInitializers;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;

import org.livespark.formmodeler.model.FieldDefinition;
import org.livespark.formmodeler.model.impl.basic.selectors.DefaultSelectorOption;
import org.livespark.formmodeler.model.impl.basic.selectors.SelectorFieldBase;
import org.livespark.formmodeler.renderer.backend.service.impl.DMOBasedTransformerContext;
import org.livespark.formmodeler.renderer.backend.service.impl.FieldSetting;

/**
 * @author Pere Fernandez <pefernan@redhat.com>
 */
@Dependent
public class EnumSelectorFieldInitializer implements FieldInitializer<SelectorFieldBase> {

    @Override
    public boolean supports( FieldDefinition field ) {
        return field instanceof SelectorFieldBase && field.getFieldTypeInfo().isEnum();
    }

    @Override
    public void initializeField( SelectorFieldBase field, FieldSetting setting, DMOBasedTransformerContext context ) {

        try {
            Enum[] enumValues = (Enum[])Class.forName( setting.getType() ).getEnumConstants();

            if ( enumValues != null && ( field.getOptions() == null || field.getOptions().isEmpty() ) ) {
                List<DefaultSelectorOption<Enum>> options = new ArrayList<>();
                for ( Enum enumConstant : enumValues ) {
                    DefaultSelectorOption<Enum> selectorOption = new DefaultSelectorOption<>(
                            enumConstant, enumConstant.toString(), false);

                    options.add( selectorOption );
                }
                field.setOptions( options );
            }

        } catch ( Exception e ) {
            e.printStackTrace();
        }



    }
}
