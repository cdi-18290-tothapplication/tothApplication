import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class FormationSessionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-formation-session div table .btn-danger'));
  title = element.all(by.css('jhi-formation-session div h2#page-heading span')).first();

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

export class FormationSessionUpdatePage {
  pageTitle = element(by.id('jhi-formation-session-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  beginInput = element(by.id('field_begin'));
  endInput = element(by.id('field_end'));
  studientsSelect = element(by.id('field_studients'));
  documentsSelect = element(by.id('field_documents'));
  formationSelect = element(by.id('field_formation'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setBeginInput(begin) {
    await this.beginInput.sendKeys(begin);
  }

  async getBeginInput() {
    return await this.beginInput.getAttribute('value');
  }

  async setEndInput(end) {
    await this.endInput.sendKeys(end);
  }

  async getEndInput() {
    return await this.endInput.getAttribute('value');
  }

  async studientsSelectLastOption(timeout?: number) {
    await this.studientsSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async studientsSelectOption(option) {
    await this.studientsSelect.sendKeys(option);
  }

  getStudientsSelect(): ElementFinder {
    return this.studientsSelect;
  }

  async getStudientsSelectedOption() {
    return await this.studientsSelect.element(by.css('option:checked')).getText();
  }

  async documentsSelectLastOption(timeout?: number) {
    await this.documentsSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async documentsSelectOption(option) {
    await this.documentsSelect.sendKeys(option);
  }

  getDocumentsSelect(): ElementFinder {
    return this.documentsSelect;
  }

  async getDocumentsSelectedOption() {
    return await this.documentsSelect.element(by.css('option:checked')).getText();
  }

  async formationSelectLastOption(timeout?: number) {
    await this.formationSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async formationSelectOption(option) {
    await this.formationSelect.sendKeys(option);
  }

  getFormationSelect(): ElementFinder {
    return this.formationSelect;
  }

  async getFormationSelectedOption() {
    return await this.formationSelect.element(by.css('option:checked')).getText();
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

export class FormationSessionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-formationSession-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-formationSession'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
