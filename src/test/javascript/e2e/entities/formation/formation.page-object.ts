import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class FormationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-formation div table .btn-danger'));
  title = element.all(by.css('jhi-formation div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class FormationUpdatePage {
  pageTitle = element(by.id('jhi-formation-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  labelInput = element(by.id('field_label'));
  descInput = element(by.id('field_desc'));
  ccpSelect = element(by.id('field_ccp'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setLabelInput(label) {
    await this.labelInput.sendKeys(label);
  }

  async getLabelInput() {
    return await this.labelInput.getAttribute('value');
  }

  async setDescInput(desc) {
    await this.descInput.sendKeys(desc);
  }

  async getDescInput() {
    return await this.descInput.getAttribute('value');
  }

  async ccpSelectLastOption(timeout?: number) {
    await this.ccpSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async ccpSelectOption(option) {
    await this.ccpSelect.sendKeys(option);
  }

  getCcpSelect(): ElementFinder {
    return this.ccpSelect;
  }

  async getCcpSelectedOption() {
    return await this.ccpSelect.element(by.css('option:checked')).getText();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class FormationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-formation-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-formation'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
